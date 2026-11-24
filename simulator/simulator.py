"""
Device Data Simulator - Energy Management System

Every `interval_minutes` of simulated time, it generates for each configured device
the energy consumed in that interval (kWh) and sends it to the RabbitMQ
`measurements` queue as JSON:

    {"timestamp": "2026-11-20T18:10:00", "device_id": "...", "measurement_value": 0.231}

Run:  pip install -r requirements.txt
      python simulator.py          (settings in config.json)
"""

import json
import random
import time
from datetime import datetime, timedelta

import pika

# Share of a device's base load used at each hour of the day (0-23):
# low at night, a bump in the morning, moderate during the day, peak in the evening.
HOURLY_PROFILE = [
    0.35, 0.30, 0.30, 0.30, 0.30, 0.35,   # 00-05
    0.60, 0.95, 0.90, 0.65, 0.60, 0.60,   # 06-11
    0.70, 0.65, 0.60, 0.60, 0.75, 1.00,   # 12-17
    1.30, 1.45, 1.40, 1.20, 0.85, 0.55,   # 18-23
]


def generate_value(base_kw, timestamp, interval_minutes):
    """Energy (kWh) consumed in one interval: base load x time of day x small noise."""
    power_kw = base_kw * HOURLY_PROFILE[timestamp.hour] * random.uniform(0.9, 1.1)
    return round(power_kw * interval_minutes / 60, 3)


def main():
    with open("config.json", encoding="utf-8") as f:
        cfg = json.load(f)

    rabbit = cfg["rabbitmq"]
    interval = cfg["interval_minutes"]
    delay = cfg["seconds_between_readings"]
    current = datetime.fromisoformat(cfg["start"])
    end = current + timedelta(hours=cfg["duration_hours"])

    # Each device gets its own random average load (kW), so devices differ.
    devices = {device_id: random.uniform(0.4, 1.2) for device_id in cfg["devices"]}

    connection = pika.BlockingConnection(pika.ConnectionParameters(
        host=rabbit["host"],
        port=rabbit["port"],
        credentials=pika.PlainCredentials(rabbit["username"], rabbit["password"]),
    ))
    channel = connection.channel()
    # Make sure the queue exists, with the same settings monitoring-service uses.
    channel.queue_declare(queue=rabbit["queue"], durable=True)

    print(f"Simulating {len(devices)} device(s) from {current} to {end}. Ctrl+C to stop.")
    try:
        while current < end:
            for device_id, base_kw in devices.items():
                message = {
                    "timestamp": current.strftime("%Y-%m-%dT%H:%M:%S"),
                    "device_id": device_id,
                    "measurement_value": generate_value(base_kw, current, interval),
                }
                channel.basic_publish(
                    exchange="",                      # default exchange: routes by queue name
                    routing_key=rabbit["queue"],
                    body=json.dumps(message),
                    properties=pika.BasicProperties(
                        content_type="application/json",   # so Spring parses it as JSON
                        delivery_mode=2,                   # persistent message
                    ),
                )
                print(f"Sent: {message}")
            current += timedelta(minutes=interval)
            time.sleep(delay)
    except KeyboardInterrupt:
        print("Stopped.")
    finally:
        connection.close()


if __name__ == "__main__":
    main()