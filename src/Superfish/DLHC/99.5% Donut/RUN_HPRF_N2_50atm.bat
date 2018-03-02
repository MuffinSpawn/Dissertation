start /W  " "  "%SFDIR%automesh"  HPRF_N2_50atm
start /W  " "  "%SFDIR%cfish"     HPRF_N2_50atm
start /W  " "  "%SFDIR%SFO"       HPRF_N2_50atm.t35 SegmentFile.seg

copy outaut.txt outaut2.txt
copy outfis.txt outfis2.txt

if (%1)==(p) start /W  " "  "%SFDIR%WSFplot" HPRF_N2_50atm.t35 3