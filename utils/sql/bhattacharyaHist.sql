set serveroutput on;

CREATE OR REPLACE Function batacharyaHist( nameInput1 IN varchar2, nameInput2 IN varchar2) RETURN double precision
 
IS
  
  imageinput1 MULTIMEDIA%rowtype;
  imageinput2 MULTIMEDIA%rowtype;
  resultat double precision;
  histtmp HISTOGRAMM; 
  
  sumh1 double precision := 0;
  sumh2 double precision := 0;
  multsumh1h2 double precision := 0;
  BC double precision := 0;
  s integer := 256;
  tab MULTIMEDIA.hist%type;
  tab2 MULTIMEDIA.hist%type;
  nbTotal integer := 1;
  
BEGIN

  SELECT * INTO imageinput1 FROM MULTIMEDIA WHERE nom = nameInput1;
  SELECT * INTO imageinput2 FROM MULTIMEDIA WHERE nom = nameInput2;
  
  nbTotal := imageinput1.image.getHeight() * imageinput1.image.getWidth();
  tab:= imageinput1.hist;
  tab2:= imageinput2.hist;
  
  for it in 1..tab.count loop
   sumh1 := sumh1 + tab(it)/nbTotal;
   sumh2 := sumh2 + tab2(it)/nbTotal;
  end loop;
  
  multsumh1h2 := sumh1*sumh2;
  
  for it in 1..tab.count loop
    BC := BC + SQRT( (tab(it)/nbTotal * tab2(it)/nbTotal) / multsumh1h2);
  end loop;
  
  resultat := -LN(BC);

  return resultat;

END;
/

CREATE OR REPLACE TYPE BATRESULT IS TABLE OF VARCHAR2(30) ;
/
CREATE OR REPLACE Function batacharyaHistLoop( nameInput IN varchar2, seuil IN double precision) RETURN BATRESULT
 
IS

  c1result MULTIMEDIA%ROWTYPE;
  imageinput MULTIMEDIA%rowtype;
  batresult_tab BATRESULT := BATRESULT(); 
  indexx integer := 0;
  
  CURSOR c1
    IS
     SELECT *
     FROM MULTIMEDIA
     WHERE nom not like nameInput and hist is not null;
     
     
  BEGIN
    SELECT * INTO imageinput FROM MULTIMEDIA WHERE nom = nameInput;
    open c1;
    
    loop
    
    fetch c1 into c1result;
      if batacharyaHist( imageinput.nom, c1result.nom ) < seuil then
        batresult_tab.EXTEND (1);
        indexx := indexx + 1;
        batresult_tab(indexx) := c1result.nom;
      end if;
    exit when c1%notfound;
    
    end loop;
    
    return batresult_tab;
    
END;
   
/  

select * from table( batacharyaHistLoop('1.jpg', 0.0005));  
   
-- begin    
--  update multimedia set hist = ( select hist from multimedia where nom = '1.jpg') where nom = '10.jpg' ;  
--  commit;
-- end; 
  
CREATE OR REPLACE VIEW batacharya_cross AS
  SELECT m1.nom as nomimgref, m2.nom as nomimgcomp, batacharyaHist(m1.nom, m2.nom) As batdist
  FROM MULTIMEDIA m1,  MULTIMEDIA m2
  Where m1.hist is not NULL and  m2.hist is not NULL;
/

select DISTINCT * from batacharya_cross where batdist < 0.00005 and NOMIMGREF like '1.jpg';