--
-- PostgreSQL database dump
--

\restrict NHxN6ap1NMxnqb2omHffoDiPEN5MybehyltDCUTEFcH5uodFJZargLGKaeArQ92

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

ALTER TABLE IF EXISTS ONLY public.credentials DROP CONSTRAINT IF EXISTS ukl7xhygibdj6cgkpj2ih1jgx14;
ALTER TABLE IF EXISTS ONLY public.credentials DROP CONSTRAINT IF EXISTS credentials_pkey;
DROP TABLE IF EXISTS public.credentials;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: credentials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credentials (
    id uuid NOT NULL,
    password_hash character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    CONSTRAINT credentials_role_check CHECK (((role)::text = ANY ((ARRAY['ADMIN'::character varying, 'CLIENT'::character varying])::text[])))
);


ALTER TABLE public.credentials OWNER TO postgres;

--
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credentials (id, password_hash, role, username) FROM stdin;
cd0b02a8-ef0b-4df3-aea6-606ee2de1dbc	$2a$10$w9G8RpyQ4JHUdTzjBa6BzOdzVEAUEN9GhbMnbjvNWvGAhyCe44JH2	ADMIN	admin
093ad13a-0ccb-4b49-9384-665bdb18acc2	$2a$10$rHEMIYox3EUq1Rn7MwNi9uCxxzOabAzSLEUHXvhL8qo0aQB8.ZnVK	CLIENT	mihai
9f866694-cf89-474a-802c-de0592d3b556	$2a$10$06rJS0JpbrXGvqPHeCQcG.APTw4HjIBtgc4.2nKPzBU97neNrGYPq	CLIENT	sue
23f40ce7-9f50-407b-9814-d0f3012c7200	$2a$10$TLiQpm81VtlClvey/ls5gu9RMdqOjglDfM8T0UPOjKkWS1zUzpeHW	ADMIN	admin1
ea324acf-7f41-4220-84ad-722d7846a176	$2a$10$/KRVP.HG5I9B/qqQjPlIFuhTOz26U8qXXrcnxp.Rj0fhFWZVwlA7G	CLIENT	flori
\.


--
-- Name: credentials credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (id);


--
-- Name: credentials ukl7xhygibdj6cgkpj2ih1jgx14; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT ukl7xhygibdj6cgkpj2ih1jgx14 UNIQUE (username);


--
-- PostgreSQL database dump complete
--

\unrestrict NHxN6ap1NMxnqb2omHffoDiPEN5MybehyltDCUTEFcH5uodFJZargLGKaeArQ92

