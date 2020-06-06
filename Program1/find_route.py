import sys
from Queue import Queue
from heapq import heappush, heappop

#priority queue is not in python2 so class is created
class priority_queue(Queue):
     def _init(self, maxsize):
         self.maxsize = maxsize
         self.queue = []
     def _put(self, item):
         return heappush(self.queue, item)
     def _get(self):
         return heappop(self.queue)

#uninformed_search function 
def uninfromed_search(graph,source,destination):
  visited=set()
  path=[]
  queue=priority_queue()
  queue.put((0, [source]))
  nodesvisited=0

  while queue:

    if queue.empty():
      print ('Nodes expanded= %d \ndistance: infinity\nroute: none'%(nodesvisited))
      return 

    cost, path = queue.get()
    node = path[len(path)-1]
    if node not in visited:
      visited.add(node)
      if node == destination:
        path.append(cost)
        print ('Nodes expanded: %d'%(nodesvisited))
        return path

      for a in neighbours(graph, node):
        if a not in visited:
          total = cost + int(get_cost(graph, node, a))
          temp = path[:]
          temp.append(a)
          queue.put((total, temp))
          nodesvisited +=1

#sets neighbours to graph
def neighbours(graph,node):
  elements = graph[node]
  return [x[0] for x in elements]

#function to get cost 
def get_cost(graph, from_node, to_node):
  position = [x[0] for x in graph[from_node]].index(to_node)
  return graph[from_node][position][1]


#creates graph
def create_graph(filename):
	graph={}
	txt=open(filename,'r')
	for line in txt:
		if 'END OF INPUT' in line:
			return graph
		nodeA,nodeB,dist=line.split()
		graph.setdefault(nodeA,[]).append((nodeB,dist))
		graph.setdefault(nodeB,[]).append((nodeA,dist))

#display function to print 
def display(graph,path):
  dist = path[-1]
  print ('Distance: %sKm'%(dist))
  print ('Route: ')
  for x in path[:-2]:
    y = path.index(x)
    position = [z[0] for z in graph[x]].index(path[y+1])
    cost = graph[x][position][1]
    print ('%s to %s, %s Km' %(x,path[y+1],cost))

#main function takes command line arguments
def main():
	filename=sys.argv[1]
	source=sys.argv[2]
	destination=sys.argv[3]
	graph={}
	graph=create_graph(filename)
	if source not in graph.keys():
		print("Improper source")
		sys.exit()
	if destination not in graph.keys():
		print("Improper destination")
		sys.exit()

	path=[]
	path=uninfromed_search(graph,source,destination)

	if path:
		display(graph,path)

if __name__ == '__main__':
	main()