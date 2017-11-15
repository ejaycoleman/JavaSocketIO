import random

goes = []

def playTurn(i):
    tapped = input("which was "+str(i)+"?")
    print(goes[i])
    print(tapped)
    
    if int(goes[i]) == int(tapped):
        print("ya")
        return True
    else:
        print("na")
        return False

def play():
    failed = False
    show = 0
    for i in range(len(goes)):
        if (playTurn(i) == True):
            print("correct")
        else:
            print("incorrect")
            failed = True
            break

    if failed:
        fail()
    else:
        light()
    


    
def fail():
    score = len(goes) -1
    print("Noo! You Failed! your score was: "+str(score))

def light():
    randomLight = random.randint(1,4)
    goes.append(randomLight)
    for i in range(len(goes)):
        # socket.emit("lightFinch", goes[i])
        print(goes[i])
        
    play()



light()

