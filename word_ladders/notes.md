# Solution

Initially, I believed that an implementation using primitives (like int, int[]) and doing all iterations using for-loops would be much faster than using Java collection classes like ArrayList or Queue, since they would provide overhead that I had no control over. This meant the implementation ended up being more imperative and C-like. 

After finishing the imperative implementation, I also implemented one using ArrayList, which I had previously avoided, and noticed a huge difference in speed. The new implementation finished one of the larger tests about 100x faster. Not only that, I found the code more readable as I ended up with a more object-oriented implementation.

I am certain that my first implementation was not optimal and would have been as fast, if not faster than the second implementation if I had spent some time tweaking the array sizes and class attributes. However, I didn't find it necessary to optimize the algorithm at the time. Furthermore, both solutions most likely end up having quite similar bytecode after compilation and there wouldn't be much difference in speed.