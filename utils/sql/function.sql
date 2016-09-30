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
  tab MULTIMEDIA.hist%type;
  tab2 MULTIMEDIA.hist%type;
  nbTotal integer := 1;
  
BEGIN

  SELECT * INTO imageinput1 FROM MULTIMEDIA WHERE nom like nameInput1;
  SELECT * INTO imageinput2 FROM MULTIMEDIA WHERE nom like nameInput2;
  
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

CREATE OR REPLACE procedure batacharyaHistProc(batresult_tab OUT BATRESULT, nameInput IN varchar2, seuil IN double precision )
 
IS

  c1result MULTIMEDIA%ROWTYPE;
  imageinput MULTIMEDIA%rowtype; 
  indexx integer := 0;
   
  
  CURSOR c1
    IS
     SELECT *
     FROM MULTIMEDIA
     WHERE nom not like nameInput and hist is not null;
     
     
  BEGIN
    batresult_tab := BATRESULT();
    SELECT * INTO imageinput FROM MULTIMEDIA WHERE nom like nameInput;

    open c1;
    
    loop
    
    fetch c1 into c1result;
    exit when c1%notfound;
      if batacharyaHist( imageinput.nom, c1result.nom ) < seuil then
        batresult_tab.EXTEND (1);
        indexx := indexx + 1;
        batresult_tab(indexx) := c1result.nom;
      end if;
    
    
    end loop;
  
   
END;
   
/  

CREATE OR REPLACE Function batacharyaTx( nameInput1 IN varchar2, nameInput2 IN varchar2) RETURN double precision
 
IS
  
  imageinput1 MULTIMEDIA%rowtype;
  imageinput2 MULTIMEDIA%rowtype;
  resultat double precision;
  type trio IS VARRAY(3) OF double precision;
  txtab1 trio;
  txtab2 trio;  
  
  sumh1 double precision := 0;
  sumh2 double precision := 0;
  multsumh1h2 double precision := 0;
  BC double precision := 0;
  nbTotal integer := 1;
  
BEGIN
  
  SELECT * INTO imageinput1 FROM MULTIMEDIA WHERE nom like nameInput1;
  SELECT * INTO imageinput2 FROM MULTIMEDIA WHERE nom like nameInput2;
  
  txtab1(1) := imageinput1.TXR;
  txtab1(2) := imageinput1.TXG;
  txtab1(3) := imageinput1.TXB;
  
  txtab2(1) := imageinput2.TXR;
  txtab2(2) := imageinput2.TXG;
  txtab2(3) := imageinput2.TXB;
  
  for it in 1..txtab1.count loop
   sumh1 := sumh1 + txtab1(it);
   sumh2 := sumh2 + txtab2(it);
  end loop;
  
  multsumh1h2 := sumh1*sumh2;
  
  for it in 1..txtab1.count loop
    BC := BC + SQRT( (txtab1(it)/nbTotal * txtab2(it)/nbTotal) / multsumh1h2);
  end loop;
  
  resultat := -LN(BC);

  return resultat;

END;
/

CREATE OR REPLACE procedure batacharyaTxProc(batresult_tab OUT BATRESULT, nameInput IN varchar2, seuil IN double precision )
 
IS

  c1result MULTIMEDIA%ROWTYPE;
  imageinput MULTIMEDIA%rowtype; 
  indexx integer := 0;
   
  
  CURSOR c1
    IS
     SELECT *
     FROM MULTIMEDIA
     WHERE nom not like nameInput and hist is not null;
     
     
  BEGIN
    batresult_tab := BATRESULT();
    SELECT * INTO imageinput FROM MULTIMEDIA WHERE nom like nameInput;

    open c1;
    
    loop
    
    fetch c1 into c1result;
    exit when c1%notfound;
      if batacharyaTx( imageinput.nom, c1result.nom ) < seuil then
        batresult_tab.EXTEND (1);
        indexx := indexx + 1;
        batresult_tab(indexx) := c1result.nom;
      end if;
    
    
    end loop;
  
   
END;
   
/  

CREATE OR REPLACE function batacharyaTxFunc(nameInput IN varchar2, seuil IN double precision ) RETURN BATRESULT
 
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
    batresult_tab := BATRESULT();
    SELECT * INTO imageinput FROM MULTIMEDIA WHERE nom like nameInput;

    open c1;
    
    loop
    
    fetch c1 into c1result;
      exit when c1%notfound;
      if batacharyaTx( imageinput.nom, c1result.nom ) < seuil then
        batresult_tab.EXTEND (1);
        indexx := indexx + 1;
        batresult_tab(indexx) := c1result.nom;
      end if;
    
    
    end loop;
    
    return batresult_tab;
   
END;
   
/  


CREATE OR REPLACE TYPE SIGRESULT IS TABLE OF VARCHAR2(30) ;
/
CREATE OR REPLACE procedure SigLoop(  sigresult_tab OUT SIGRESULT, nameInput IN varchar2, color IN integer, texture IN integer, shape IN integer, locationn IN integer, seuil IN integer) 
IS

  c1result MULTIMEDIA%ROWTYPE;
  sim integer;
  sig ordsys.ordimageSignature;
  indexx integer := 0;
  
  CURSOR c1
    IS
     SELECT *
     FROM MULTIMEDIA
     WHERE nom not like nameInput;
     
     
  BEGIN
  
    sigresult_tab := SIGRESULT();
    
    select signature into sig from multimedia where nom = nameInput; 
    open c1;
    
    loop
    
      fetch c1 into c1result;
       exit when c1%notfound;
        sim := ordsys.ordimageSignature.isSimilar(sig, c1result.signature, 'color =  ' || color || ', texture =' || texture || ', shape = ' || shape || ', location = ' || locationn, seuil);
        if sim = 1 then
          sigresult_tab.EXTEND (1);
          indexx := indexx + 1;
          sigresult_tab(indexx) := c1result.nom;
          
        end if;
     
    
    end loop;

END;
/

CREATE OR REPLACE procedure NBPIXELSLoop( sigresult_tab OUT SIGRESULT, nameInput IN varchar2, seuil IN integer) 
IS

  c1result MULTIMEDIA%ROWTYPE;
  sim integer;
  nbPixels integer;
  indexx integer := 0;
  
  CURSOR c1
    IS
     SELECT *
     FROM MULTIMEDIA
     WHERE nom not like nameInput;
     
     
  BEGIN
    sigresult_tab := SIGRESULT();
    select NBPIXELS into nbPixels from multimedia where nom = nameInput; 
    open c1;
    
    loop
    
      fetch c1 into c1result;
      exit when c1%notfound; 
        if abs(nbPixels-c1result.NBPIXELS) < seuil then   
          sigresult_tab.EXTEND (1);
          indexx := indexx + 1;
          sigresult_tab(indexx) := c1result.nom;  
        end if;
      
    
    end loop;

END;
/