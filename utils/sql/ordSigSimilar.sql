set serveroutput on;

CREATE OR REPLACE TYPE SIGRESULT IS TABLE OF VARCHAR2(30) ;
/
CREATE OR REPLACE Function SigLoop( nameInput IN varchar2, color IN integer, texture IN integer, shape IN integer, locationn IN integer, seuil IN integer) RETURN SIGRESULT
IS

  c1result MULTIMEDIA%ROWTYPE;
  sim integer;
  sig ordsys.ordimageSignature;
  indexx integer := 0;
  batresult_tab SIGRESULT := SIGRESULT(); 
  
  CURSOR c1
    IS
     SELECT *
     FROM MULTIMEDIA
     WHERE nom not like nameInput;
     
     
  BEGIN
  
    select signature into sig from multimedia where nom = nameInput; 
    open c1;
    
    loop
    
      fetch c1 into c1result;
        sim := ordsys.ordimageSignature.isSimilar(sig, c1result.signature, 'color =  ' || color || ', texture =' || texture || ', shape = ' || shape || ', location = ' || locationn, seuil);
        if sim = 1 then
          batresult_tab.EXTEND (1);
          indexx := indexx + 1;
          batresult_tab(indexx) := c1result.nom;
          
        end if;
      exit when c1%notfound;
    
    end loop;
    
    return batresult_tab;

END;
/

SELECT * FROM table( SigLoop('1.jpg', 0.5, 0.7, 0, 0, 10));  