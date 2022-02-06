CREATE TABLE IF NOT EXISTS object(
        obj_id serial NOT NULL,
	    owner integer DEFAULT 0,
	    name character varying  NOT NULL,
	    date date NOT NULL,
	    type character varying NOT NULL,
	    CONSTRAINT "Object_pkey" PRIMARY KEY (obj_id)
	);
	
	CREATE TABLE IF NOT EXISTS attribute(
    id serial NOT NULL,
    name character varying NOT NULL,
    CONSTRAINT "Attribute_pkey" PRIMARY KEY (id)
);

	CREATE TABLE IF NOT EXISTS parameterDto(
    obj_id integer NOT NULL,
    attr_id integer NOT NULL,
    value character varying NOT NULL,
    CONSTRAINT "Parameter_pkey" PRIMARY KEY (obj_id, attr_id),
    CONSTRAINT attr_id FOREIGN KEY (attr_id)
        REFERENCES Attribute(id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT obj_id FOREIGN KEY (obj_id)
        REFERENCES Object (obj_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);