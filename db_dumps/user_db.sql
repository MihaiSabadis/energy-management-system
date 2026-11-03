--
-- PostgreSQL database dump
--

\restrict vpfkERaK4zuMQen1jAg1XHbjqaGuYvrvE5WkZUHOnZyDbEXfxM0JEURmZnndZtx

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

ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS users_pkey;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS ukr43af9ap4edm43mmtq01oddj6;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS uk6dotkott2kjsp8vw4d0m25fb7;
DROP TABLE IF EXISTS public.users;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    address character varying(255),
    email character varying(255) NOT NULL,
    full_name character varying(255) NOT NULL,
    username character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, address, email, full_name, username) FROM stdin;
cd0b02a8-ef0b-4df3-aea6-606ee2de1dbc	\N	admin@ems.local	Administrator	admin
093ad13a-0ccb-4b49-9384-665bdb18acc2	Str. Unirii	mihai@yahoo.com	Mihai Sabadis	mihai
9f866694-cf89-474a-802c-de0592d3b556	Seasame Street	sue@yahoo.com	Sue Jackson	sue
23f40ce7-9f50-407b-9814-d0f3012c7200	\N	admin1@test.com	admin1	admin1
ea324acf-7f41-4220-84ad-722d7846a176	Str. Daicoviciu	flori@user.com	Florina Popescu	flori
\.


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: users ukr43af9ap4edm43mmtq01oddj6; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

\unrestrict vpfkERaK4zuMQen1jAg1XHbjqaGuYvrvE5WkZUHOnZyDbEXfxM0JEURmZnndZtx

