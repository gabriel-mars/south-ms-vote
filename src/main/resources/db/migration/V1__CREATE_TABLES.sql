-- public.assembly definition
-- Drop table
-- DROP TABLE public.assembly;
CREATE TABLE public.assembly (
	id int8 NOT NULL,
	available bool NOT NULL,
	created_date timestamp NOT NULL,
	description varchar(255) NOT NULL,
	ending_date date NOT NULL,
	starting_date date NOT NULL,
	assembly_status varchar(10) NOT NULL,
	CONSTRAINT assembly_pkey PRIMARY KEY (id)
);

-- public.associate definition
-- Drop table
-- DROP TABLE public.associate;
CREATE TABLE public.associate (
	id int8 NOT NULL,
	cpf varchar(15) NOT NULL,
	created_date timestamp NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT associate_pkey PRIMARY KEY (id)
);

-- public.vote definition
-- Drop table
-- DROP TABLE public.vote;
CREATE TABLE public.vote (
	id int8 NOT NULL,
	created_date timestamp NOT NULL,
	vote_type varchar(3) NOT NULL,
	assembly_id int8 NULL,
	associate_id int8 NULL,
	CONSTRAINT vote_pkey PRIMARY KEY (id)
);

-- public.vote foreign keys
ALTER TABLE public.vote ADD CONSTRAINT fk44t3dfknuwdiil2n8rombwf0g FOREIGN KEY (assembly_id) REFERENCES public.assembly(id);
ALTER TABLE public.vote ADD CONSTRAINT fk5ki8q5kgx4bmjd1jh473hqbra FOREIGN KEY (associate_id) REFERENCES public.associate(id);
