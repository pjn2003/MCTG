--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cardpacks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cardpacks (
    id integer NOT NULL,
    packname character varying(64),
    card_list integer[]
);


ALTER TABLE public.cardpacks OWNER TO postgres;

--
-- Name: cards; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cards (
    id integer NOT NULL,
    name character varying(32),
    basedamage integer,
    element character varying(32),
    cardtype character varying(32),
    monstertype character varying(32)
);


ALTER TABLE public.cards OWNER TO postgres;

--
-- Name: mtcguser; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mtcguser (
    username character varying(32) NOT NULL,
    password character varying(32) NOT NULL,
    coins integer,
    bio character varying(500),
    elo integer,
    wins integer,
    losses integer,
    is_admin boolean,
    cards integer[],
    deck integer[],
    cardsinstore integer[]
);


ALTER TABLE public.mtcguser OWNER TO postgres;

--
-- Name: tradedeals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tradedeals (
    dealid integer NOT NULL,
    cardid integer,
    card_type character varying(32),
    card_mindmg integer,
    username character varying(32)
);


ALTER TABLE public.tradedeals OWNER TO postgres;

--
-- Data for Name: cardpacks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cardpacks (id, packname, card_list) FROM stdin;
1	Beginner Pack	{1,2,3,7,22}
2	Horde of Dragons	{5,10,23,24,25}
3	Knights of the Realm	{4,28,27,31}
4	The Dragonslayer	{26}
5	Ferocious Fiends	{9,12,8,11}
6	Elven Kingdoms	{6,1,29,30}
7	Arcane Academy	{7,13,14,20,15,17}
8	Ultimate Magicks	{18,19,21}
9	Cards that start with S	{4,16,22,28,29,31}
10	test_pack	{1,3,4,5,6,7}
\.


--
-- Data for Name: cards; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cards (id, name, basedamage, element, cardtype, monstertype) FROM stdin;
4	Steel Knight	16	Normal	Monster	Knight
5	Frost Wyvern	32	Water	Monster	Dragon
6	Xavier the Volcano Lord	25	Fire	Monster	Elf
7	Wizzy the Wizard	12	Normal	Monster	Wizard
8	Big Berthold	30	Normal	Monster	Ork
9	The Kraken	50	Water	Monster	Kraken
10	Grigori	50	Fire	Monster	Dragon
11	Hobgoblin	15	Normal	Monster	Goblin
12	Lich	35	Normal	Monster	Wizard
13	Gundula	150	Normal	Monster	Wizard
14	Ice Shards	40	Water	Spell	\N
15	Laser Beam	26	Fire	Spell	\N
16	Solar Flare	52	Fire	Spell	\N
17	Darkness	19	Normal	Spell	\N
18	Unmake	24	Normal	Spell	\N
19	Nihil	999	Normal	Spell	\N
20	Tsunami	31	Water	Spell	\N
21	Omega Cannon	500	Normal	Spell	\N
22	Spark	1	Fire	Spell	\N
3	Wood Goblin	1	Normal	Monster	Goblin
1	Forest Elf	3	Normal	Monster	Elf
2	Fireball	6	Fire	Spell	\N
23	Ancient Dragon	120	Normal	Monster	Dragon
24	Draconic Hatchling	20	Fire	Monster	Dragon
25	Dragon Egg	0	Normal	Monster	Dragon
26	Estinien the Dragonslayer	80	Normal	Monster	Knight
27	Champion of Frost	65	Water	Monster	Knight
28	Steelier Knight	32	Normal	Monster	Knight
29	Snow Elf	26	Water	Monster	Elf
30	Xillian III., King of the Elves	59	Normal	Monster	Elf
31	Soldier	8	Normal	Monster	Knight
\.


--
-- Data for Name: mtcguser; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mtcguser (username, password, coins, bio, elo, wins, losses, is_admin, cards, deck, cardsinstore) FROM stdin;
admin	istrator	20	\N	721	251	3	\N	{2,4,6,8}	{2,4,6,8}	{0}
altenhof	markus	20	\N	358	160	34	\N	{1,3,5,7,9}	{3,5,7,9}	{0}
kienboec	daniel	20	\N	120	45	67	\N	{2,4,3,9}	{2,4,3,9}	{0}
mihaicaliburn	magic92	15	Hello there	500	150	0	\N	{21,19,16,2,26,18}	{26,21,19,16}	{0}
p_neuhold	afe32radf	100	\N	700	200	0	\N	{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18}	{1,2,3,4}	{0}
test_user2	gggggakdwo	20	\N	\N	\N	\N	\N	\N	\N	{0}
test_user	abcdef123	15	my newest bio!	914	100	44	t	{1,2,3,4,5,6,1,3,4,5,6,7}	{4,5,6,3}	{0}
\.


--
-- Data for Name: tradedeals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tradedeals (dealid, cardid, card_type, card_mindmg, username) FROM stdin;
1	5	Spell	30	test_user
2	6	Monster	20	test_user
\.


--
-- Name: cardpacks cardpacks_packname_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cardpacks
    ADD CONSTRAINT cardpacks_packname_key UNIQUE (packname);


--
-- Name: cardpacks cardpacks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cardpacks
    ADD CONSTRAINT cardpacks_pkey PRIMARY KEY (id);


--
-- Name: cards cards_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cards
    ADD CONSTRAINT cards_pkey PRIMARY KEY (id);


--
-- Name: mtcguser mtcguser_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mtcguser
    ADD CONSTRAINT mtcguser_pkey PRIMARY KEY (username);


--
-- Name: tradedeals tradedeals_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tradedeals
    ADD CONSTRAINT tradedeals_pkey PRIMARY KEY (dealid);


--
-- Name: tradedeals card_id_foreignkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tradedeals
    ADD CONSTRAINT card_id_foreignkey FOREIGN KEY (cardid) REFERENCES public.cards(id);


--
-- PostgreSQL database dump complete
--

