set serveroutput on;


CREATE OR REPLACE TYPE HISTOGRAMM IS VARRAY(256) OF INTEGER;
--drop table multimedia;

--creation de la table d'images
--create table multimedia(
--  nom varchar2(50),
--  image ordsys.ordimage,
--  signature ordsys.ordimageSignature,
--  hist HISTOGRAMM,
--  txR integer,
--  txG integer,
--  txB integer,
--  nbPixels integer,
--  color integer
--);

declare 
  i ordsys.ordimage;
  ctx RAW(400) := NULL;
  ligne multimedia%ROWTYPE;
  cursor mm is select * from multimedia for update;
  sig1 ordsys.ordimageSignature;
  sig2 ordsys.ordimageSignature;
  sim integer;

--chargement des images

for indeximage in  1..10 loop

  insert into multimedia(nom, image, signature) values (CONCAT (to_char(indeximage) ,'.jpg'), ordsys.ordimage.init(), ordsys.ordimageSignature.init());
  
  select image into i from multimedia where nom = CONCAT (to_char(indeximage) ,'.jpg') for update;
  i.importFrom(ctx, 'file', 'IMG', CONCAT (to_char(indeximage) ,'.jpg'));
  update multimedia set image = i where nom = CONCAT (to_char(indeximage) ,'.jpg');
 
end loop;

-- generation des signatures
for ligne in mm loop
  ligne.signature.generateSignature(ligne.image);
  update multimedia set signature = ligne.signature where current of mm;
end loop;
commit;

end;