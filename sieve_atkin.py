import time
def sieve_atkin(limit,count):
    nums=[0]*(limit+1)

    if limit>2:
        nums[2]=1
    if limit>3:
        nums[3]=1


    for i in range(1,int((limit)**0.5)+1):
        for j in range(1,int((limit)**0.5)+1):


            n=(4*i*i)+(j*j)
            if n<=limit:
                if (n%12==5 or n%12==1):
                    nums[n]^=1

            n=(3*i*i)+(j*j)
            if n<=limit:
                if (n%12==7):
                    nums[n]^=1

            n=(3*i*i)-(j*j)
            if i>j and n<=limit:
                if n%12==11:
                    nums[n]^=1

    for i in range(5,int(limit**0.5)+1):
        if nums[i]==1:
            for j in range(i*i,limit+1,i*i):
                nums[j]=0

    for i in range(2,limit+1):
        if nums[i]==1:
            count+=1
    return count

start=time.perf_counter()
limit=int(input("Enter limit:"))
c=sieve_atkin(limit,0)
end=time.perf_counter()
print(f"Number of primes from 1 to {limit} is {c}")
print(f"Time taken is seconds: {end-start}")
