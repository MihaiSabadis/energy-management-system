--
-- PostgreSQL database dump
--

\restrict cP3Crxu2feOt9MzwXdCLbNg1xiVVwkmAUyBsFbMxtO971MTMbawRIgB4GpOizuK

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
5b178cf7-c4d8-4843-a37c-7b10f5bf8ccd	\N	admin@ems.local	Administrator	admin
26277768-d78b-49bf-8af5-9d92a89a68d6	Str. Borsec	saba@yahoo.com	Mihai Sabadis	mihai
7e41470e-94f5-49ae-a26e-1c7091fe1ac1	Watkins Street, no. 78	randy@gmail.com	Randy Dandy	randy
8ddae243-9419-4486-99e7-ab27a4fdb502	Factory Street, nr. 45	stefy04@gmail.com	Stefan Red	stefy
da36220b-3e03-4b9a-bf27-9da832505bd2	\N	admin@yahoo.com	-	new_admin
b95b52a4-34ee-48f5-9461-e0c78b9f3534	Sugar Street, nr.696	bog@yahoo.com	Bogdan Man	bogo
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

\unrestrict cP3Crxu2feOt9MzwXdCLbNg1xiVVwkmAUyBsFbMxtO971MTMbawRIgB4GpOizuK

