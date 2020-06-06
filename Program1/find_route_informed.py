import sys
#takes command line arguments
arg1=sys.argv[1]
arg2=sys.argv[2]
arg3=sys.argv[3]
file_2=sys.argv[4]
source, destination, distance, source_h, distance_h = [], [], [], [], []
fringe, fringed, visit_node = [], [], []
fringed_dict, parentdist_dic = {}, {}
distance1, route = list(), list()
parentchild_dic = {}
start = arg2
goal = arg3
d = 0
nodevisited=0
global dis_h
dis_h=0
input_file = open(arg1, 'r')
for i in input_file:
    line_striped = i.rstrip()
    split_line = line_striped.split(" ")
    if split_line[0] == 'END OF INPUT':
        break
    source.append(split_line[0])
    destination.append(split_line[1])
    distance.append(split_line[2])

input_file1 = open(file_2, 'r')
for i in input_file1:
    line_striped = i.rstrip()
    split_line = line_striped.split(" ")
    if split_line[0] == 'END OF INPUT':
        break
    source_h.append(split_line[0])
    distance_h.append(split_line[1])
#adds hurestics distance
def heuristic_dist(start):
    if len(source_h) >1:
        for i in range(0,len(source_h)):
            if source_h[i]==start:
                dish=distance_h[i]
        return dish
    else:
        return 0
#tracks the route
def track(goal, start):
    while goal != start:
        if goal not in parentchild_dic:
            print 'Nodes expanded='+str(nodevisited)
            print('Distance : Infinty\nRoute: none')
            break
        temp = parentchild_dic[goal]

        for i in range(len(source)):
            if source[i] == goal:
                if destination[i] == temp:
                    distance1.append(int(distance[i]))
            elif destination[i] == goal:
                if source[i] == temp:
                    distance1.append(int(distance[i]))
        route.append(goal)
        route.append(temp)
        goal = temp

#display function to print result
def display(total_dist):
    if total_dist==0:
        print
    else:
     print 'Nodes expanded: '+str(nodevisited)
     print 'Distance: ' + str(total_distance) + 'Km'
    
     if route > 1:
        for i in range(len(route) - 2):
            first = route.pop()
            fdis = distance1.pop()
            second = route.pop()
            print str(first) + ' to ' + str(second) + ', ' + str(fdis) + 'Km'
            if not route:
                break
     else:
        print 'route:'

#function which calculates parent distance
def parentdist(start, d):
    
    if start in parentchild_dic:
        parent = parentchild_dic[start]
        
        if not parent:
            return 0
        else:
            if parentdist_dic[parent] is not 0:
                parentdist_dic[start] = d
                return d
            else:
                d = int(parentdist_dic[parent]) + int(d)
                parentdist_dic[start] = d
                return d
    else:
        parentdist_dic[start] = d
        
        return 0
 
#adds to the fringe 
def fringeadd(start, goal, d):
    parentdistance = parentdist(start, d)
    global nodevisited
    nodevisited +=1
    
    for i in range(len(source)):
        if source[i] == start and destination[i] not in visit_node and destination[i] not in fringed_dict:
            dish_h=heuristic_dist(destination[i])
            fringed_dict[destination[i]] = str(int(distance[i]) + int(parentdistance)+ int(dis_h))
            parentchild_dic[destination[i]] = source[i]
            
        elif destination[i] == start and source[i] not in visit_node and source[i] not in fringed_dict:
            dish_h=heuristic_dist(source[i])
            fringed_dict[source[i]] = str(int(distance[i]) + int(parentdistance) + int(dis_h))
            parentchild_dic[source[i]] = destination[i]
            
    fringe_dics = sorted(fringed_dict.iteritems(), key=lambda (k, v): (v, k))
    visit_node.append(start)
    set(visit_node)
    
    state = check(fringe_dics, goal, d)
    if state == 0:
        return fringe_dics

#checks if node is in fringe dictionary 
def check(fringe_dics, goal, d):
    if len(fringe_dics) < 1 or goal == fringe_dics[0][0]:

        return 0
    else:
        f1 = fringe_dics[0][0]
        d = fringe_dics[0][1]
        del fringed_dict[f1]
        
        fringeadd(f1, goal, d)


fringe_dics = fringeadd(start, goal, d)
track(goal, start)
total_distance = sum(distance1)
display(total_distance)