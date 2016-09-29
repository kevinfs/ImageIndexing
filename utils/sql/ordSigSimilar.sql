set serveroutput on;

declare 
  sim integer;
  sig1 ordsys.ordimageSignature;
  sig2 ordsys.ordimageSignature;

begin
-- test de similarite entre image1.jpg et image2.jpg
select signature into sig1 from multimedia where nom = '1.jpg';
select signature into sig2 from multimedia where nom = '2.jpg';

sim := ordsys.ordimageSignature.isSimilar(sig1, sig2, 'color = 0.5, texture = 0, shape = 0, location = 0', 10);
if sim = 1 then 
  DBMS_OUTPUT.PUT_LINE('Images similaires');
else 
  DBMS_OUTPUT.PUT_LINE('Images non similaires');
end if;
end;