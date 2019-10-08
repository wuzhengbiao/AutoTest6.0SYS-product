Dim i,str,tmp1,tmp2,tmp3
i=1
Set fso = Wscript.CreateObject("Scripting.FileSystemObject")

do 
tmp1=".\suite1_test"
tmp2="_results.html"


filepath=tmp1&i&tmp2
'msgbox filepath

set f=fso.opentextfile(filepath)
s=replace(f.readall,"jpg<br","jpg""<br")'
f.close
set r=fso.opentextfile(filepath,2,true)
r.write s
i=i+1
if i=10 then
exit do
end if
loop

Wscript.quit
