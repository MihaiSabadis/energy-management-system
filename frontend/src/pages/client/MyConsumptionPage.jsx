import { useEffect, useState } from 'react'
import dayjs from 'dayjs'
import { Alert, Box, MenuItem, Paper, Stack, TextField, Typography } from '@mui/material'
import { BarChart } from '@mui/x-charts/BarChart'
import { DatePicker } from '@mui/x-date-pickers/DatePicker'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { apiFetch } from '../../api/client'
import { useAuth } from '../../auth/useAuth'

const HOURS = Array.from({ length: 24 }, (_, h) => String(h).padStart(2, '0'))

export default function MyConsumptionPage() {
    const { auth } = useAuth()
    const [date, setDate] = useState(dayjs())
    const [devices, setDevices] = useState([])
    const [selectedId, setSelectedId] = useState('')
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        let cancelled = false
        apiFetch(`/api/monitoring/users/${auth.userId}/consumption?date=${date.format('YYYY-MM-DD')}`)
            .then((data) => {
                if (cancelled) return
                setDevices(data)
                setError('')
                setSelectedId((prev) => (data.some((d) => d.deviceId === prev) ? prev : data[0]?.deviceId ?? ''))
            })
            .catch((err) => { if (!cancelled) setError(err.message) })
            .finally(() => { if (!cancelled) setLoading(false) })
        return () => { cancelled = true }
    }, [auth.userId, date])

    const device = devices.find((d) => d.deviceId === selectedId)
    const total = device ? device.hourly.reduce((sum, v) => sum + v, 0) : 0

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Box>
                <Typography variant="h5" sx={{ mb: 2 }}>My consumption</Typography>

                <Stack direction="row" spacing={2} sx={{ mb: 2 }}>
                    <DatePicker label="Day" value={date} onChange={(v) => v && setDate(v)} disableFuture />
                    {devices.length > 0 && (
                        <TextField select label="Device" value={selectedId} sx={{ minWidth: 220 }}
                                   onChange={(e) => setSelectedId(e.target.value)}>
                            {devices.map((d) => (
                                <MenuItem key={d.deviceId} value={d.deviceId}>{d.deviceName}</MenuItem>
                            ))}
                        </TextField>
                    )}
                </Stack>

                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

                {!loading && devices.length === 0 && !error && (
                    <Alert severity="info">No devices are assigned to you yet.</Alert>
                )}

                {device && (
                    <Paper sx={{ p: 2 }}>
                        <Typography sx={{ mb: 1 }}>
                            {device.deviceName}, {date.format('DD MMM YYYY')}: total {total.toFixed(2)} kWh
                        </Typography>
                        <BarChart
                            height={360}
                            xAxis={[{ scaleType: 'band', data: HOURS, label: 'Hour' }]}
                            yAxis={[{ label: 'Energy (kWh)' }]}
                            series={[{ data: device.hourly, label: 'kWh', valueFormatter: (v) => `${v.toFixed(3)} kWh` }]}
                        />
                    </Paper>
                )}
            </Box>
        </LocalizationProvider>
    )
}