import time
def sundaram(limit):
    n=(limit-2)//2
    # primes=[]
    count=0
    if limit>2:
       count=1 
    arr=[False]*(n+1)

    for i in range(1,n+1):
        j=i
        while i+j+2*i*j<=n:
            arr[i+j+2*i*j]=True
            j+=1
    for i in range(1,n+1):
        if not arr[i]:
            count+=1
    
    return count

limit=int(input("Enter the range:"))
start=time.perf_counter()
print(sundaram(limit))
end=time.perf_counter()
print(f"Time Taken is {end-start} secs")