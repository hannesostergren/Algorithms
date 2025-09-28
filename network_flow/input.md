# Input
First row contains the integers N M C P, where  
N = number of nodes  
M = number of edges  
C = minimum required flow  
P = number of edges to remove  
Then M rows of edges described by u_i, v_i and c_i, where  
u_i = index of node u  
v_i = index of node v  
c_i = capacity of edge  
Finally, P integers with indexes of the edges to remove, in order.  

# Example
3 3 10 3  
0 1 10  
0 2 10  
1 2 10  
0  
2  
1  

# Output
x f
Where x is the number of routes that can be removed without the maximum flow being lower than the required number and f being the resulting flow after removing the edges.