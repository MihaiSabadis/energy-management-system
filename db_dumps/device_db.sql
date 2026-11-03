--
-- PostgreSQL database dump
--

\restrict Vq8nwP4TWz6jmZ9uLieDP1Sd2rrYdCo1q5iwxbXRtDUE8siT4JJJPZRRJrBIh1I

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
caf9197f-be05-48da-9cb6-43dfb1abe899	Warms the room	1.5	Radiator	093ad13a-0ccb-4b49-9384-665bdb18acc2
464df2ec-c377-4157-bce6-64bd8b672e14	sprays water	0.7	Umidifier	9f866694-cf89-474a-802c-de0592d3b556
a4a949e3-0307-4a7e-8c1e-784e35b9710f	for the garden	0.9	Water Sprinkler	\N
ae13a302-b611-48b6-8b8d-798f327897a5	corridor light	0.6	Hall Lamp	9f866694-cf89-474a-802c-de0592d3b556
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id) FROM stdin;
cd0b02a8-ef0b-4df3-aea6-606ee2de1dbc
093ad13a-0ccb-4b49-9384-665bdb18acc2
9f866694-cf89-474a-802c-de0592d3b556
23f40ce7-9f50-407b-9814-d0f3012c7200
ea324acf-7f41-4220-84ad-722d7846a176
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

\unrestrict Vq8nwP4TWz6jmZ9uLieDP1Sd2rrYdCo1q5iwxbXRtDUE8siT4JJJPZRRJrBIh1I

