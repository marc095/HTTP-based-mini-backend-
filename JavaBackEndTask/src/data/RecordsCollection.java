package data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class RecordsCollection {
final int maxSize;
Set<Integer> userIds = new HashSet<Integer>();
List<UserScore> topUserScoreSet = new CopyOnWriteArrayList<UserScore>();

public RecordsCollection(int maxSize){
this.maxSize = maxSize;
}

public boolean canAddNewScore(Integer score){
return topUserScoreSet.size() < maxSize;
}

public void addScore(Integer score, Integer userId, Integer lvl){
if(userExist(userId)){
removeUserScore(userId);
}
UserScore user = new UserScore(score, userId, lvl);
this.userIds.add(userId);
int index= Collections.binarySearch(this.topUserScoreSet, user);
index = (index < 0)?(-(index + 1)) : index;
this.topUserScoreSet.add(index, user);
}

void removeUserScore(int id){
    for (int i = 0; i < this.topUserScoreSet.size(); i++) {
        if(this.topUserScoreSet.get(i).getUserId().equals(id)){
        this.topUserScoreSet.remove(i);
        }
    }
}

boolean userExist(int id){
return this.userIds.contains(id);
}

public Iterator<UserScore> iterator() {
return this.topUserScoreSet.iterator();
}
}
