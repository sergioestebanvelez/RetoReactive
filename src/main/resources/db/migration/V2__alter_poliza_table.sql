ALTER TABLE "polizas"
ALTER "tipo" TYPE character varying(15),
ALTER "tipo" DROP DEFAULT,
ALTER "tipo" DROP NOT NULL;
COMMENT ON COLUMN "polizas"."tipo" IS '';
COMMENT ON TABLE "polizas" IS '';