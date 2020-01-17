cd redis01
./redis-cli -h 192.168.1.5 -c -p 5980 shutdown
cd ..
cd redis02
./redis-cli -h 192.168.1.5 -c -p 5981 shutdown
cd ..
cd redis03
./redis-cli -h 192.168.1.5 -c -p 5982 shutdown
cd ..
cd redis04
./redis-cli -h 192.168.1.5 -c -p 5983 shutdown
cd ..
cd redis05
./redis-cli -h 192.168.1.5 -c -p 5984 shutdown
cd ..
cd redis06
./redis-cli -h 192.168.1.5 -c -p 5985 shutdown
cd ..
