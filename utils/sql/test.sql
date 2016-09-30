--Query for hist batacharya
select * from table(batacharyaHistFunc('1.jpg', 0.05));

--Query for Sig oracle method
select * from table(SigLoopFunc('1.jpg', 0.5, 0, 0, 0, 10));

select * from table(batacharyaTxFunc('1.jpg', 0.05));