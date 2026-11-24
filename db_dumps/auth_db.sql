--
-- PostgreSQL database dump
--

\restrict OF46Nctx1WTUoHZ7pWss9KGoGKk1upMJPSgfQeFlZ4fzPkwa3tKx8ZZWkvacOLz

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
5b178cf7-c4d8-4843-a37c-7b10f5bf8ccd	$2a$10$b7gYs1/yZQAB19Ykp1P.1.G4wSaEB.LH3o92SUZsCFnKA4efltVni	ADMIN	admin
26277768-d78b-49bf-8af5-9d92a89a68d6	$2a$10$iTz.2cO0jSnedADrEzR8euJiXEIv9U/wZQeYxJpB2iM0k/qxPHyB.	CLIENT	mihai
7e41470e-94f5-49ae-a26e-1c7091fe1ac1	$2a$10$DDwtjzGk9bc7K7DBgyl40uZWByUFqe02GQ1.bjxha5.pNbNmlgC4K	CLIENT	randy
8ddae243-9419-4486-99e7-ab27a4fdb502	$2a$10$mdxJluvyYo/ZFzbPpkP3O..4u6RrvkcJ6pmLPKXhliYMh5dzavwgm	CLIENT	stefy
da36220b-3e03-4b9a-bf27-9da832505bd2	$2a$10$z8JbhiY.zlxxzpEIU405x.d9H3oCNrX/isvxIH2F6S20keVf2NmXa	ADMIN	new_admin
b95b52a4-34ee-48f5-9461-e0c78b9f3534	$2a$10$dQ5vX5aab3Qi7wMp.OJSzejaTKqlLYtHBH3NG7rci1y8TF4hPbzNm	CLIENT	bogo
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

\unrestrict OF46Nctx1WTUoHZ7pWss9KGoGKk1upMJPSgfQeFlZ4fzPkwa3tKx8ZZWkvacOLz

