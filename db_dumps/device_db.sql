--
-- PostgreSQL database dump
--

\restrict exctGHv048RaCxGyx7pgUa2XmRq7h90p9j6DMWcS585GTbYyxrY3mDO5juImM2b

-- Dumped from database version 16.15 (Debian 16.15-1.pgdg13+2)
-- Dumped by pg_dump version 16.15 (Debian 16.15-1.pgdg13+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE IF EXISTS ONLY public.devices DROP CONSTRAINT IF EXISTS fkrfbri1ymrwywdydc4dgywe1bt;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS users_pkey;
ALTER TABLE IF EXISTS ONLY public.devices DROP CONSTRAINT IF EXISTS devices_pkey;
DROP TABLE IF EXISTS public.users;
DROP TABLE IF EXISTS public.devices;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: devices; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.devices (
    id uuid NOT NULL,
    description character varying(255),
    max_consumption double precision NOT NULL,
    name character varying(255) NOT NULL,
    user_id uuid
);


ALTER TABLE public.devices OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: devices; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.devices (id, description, max_consumption, name, user_id) FROM stdin;
6d9b03c5-7c21-4add-a786-db2ab0f7ec44	umidifies the room a little	0.4	Umidifier	\N
e4694883-0ce2-4d14-9bad-ba0322840502	regulate the termperature	0.1	Thermostat	8ddae243-9419-4486-99e7-ab27a4fdb502
df6d5137-1b33-46da-acd2-d0d24b489255	lights the room in nice way	0.2	Lava Lamp	7e41470e-94f5-49ae-a26e-1c7091fe1ac1
23b2dd72-2fa1-4091-98e4-60bd1c72980b	warms the feet	1.1	Heater	b95b52a4-34ee-48f5-9461-e0c78b9f3534
cf34a99f-031d-4a7f-9448-8dd8c7ec2f56	odorizes the room in an evil way	1.5	Odorize-inator	b95b52a4-34ee-48f5-9461-e0c78b9f3534
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id) FROM stdin;
5b178cf7-c4d8-4843-a37c-7b10f5bf8ccd
26277768-d78b-49bf-8af5-9d92a89a68d6
7e41470e-94f5-49ae-a26e-1c7091fe1ac1
8ddae243-9419-4486-99e7-ab27a4fdb502
da36220b-3e03-4b9a-bf27-9da832505bd2
b95b52a4-34ee-48f5-9461-e0c78b9f3534
\.


--
-- Name: devices devices_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devices
    ADD CONSTRAINT devices_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: devices fkrfbri1ymrwywdydc4dgywe1bt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.devices
    ADD CONSTRAINT fkrfbri1ymrwywdydc4dgywe1bt FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

\unrestrict exctGHv048RaCxGyx7pgUa2XmRq7h90p9j6DMWcS585GTbYyxrY3mDO5juImM2b

