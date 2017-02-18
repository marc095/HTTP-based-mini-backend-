package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import data.RecordsCollection;
import data.UserScore;

public class Service {
  public static final int MAX_SIZE = 20;
  HashMap<Integer,RecordsCollection> lvlScores = new HashMap<Integer,RecordsCollection>();
  Lock lvlLock = new ReentrantLock();
  HashMap<Integer, ReadWriteLock> locks = new HashMap<Integer, ReadWriteLock>();
  HashMap<Integer, Integer> userLvl = new HashMap<Integer, Integer>(); 
  
  private ReadWriteLock getLock(int lvl){
  return this.locks.get(lvl);
  }
    
  private boolean lockForLvl(int lvl) {
  return getLock(lvl)==null;
  }
    
  public String  getScoreForLvl(int lvl){
  if(lockForLvl(lvl)){return "";}
  RecordsCollection scores = lvlScores.get(lvl);
  return (scores != null) ? getResultForLvl(scores):"";
  }
  
  private List<Integer> getKeys(Integer value){
   List<Integer> keys = new ArrayList<Integer>();
   for(Integer key : this.userLvl.keySet()){
      if(this.userLvl.get(key).equals(value)){
             keys.add(key);
      }
   }
   return keys;
  }
  
  private Integer getKey(int value){
    for(Integer key : this.userLvl.keySet()){
        if(this.userLvl.get(key).equals(value)){
            return key; 
        }
    }
    return null;
  }
  
  public String getScoreForUser(int id){
  StringBuffer sb = new StringBuffer();
  List<Integer> keyings = getKeys(id);   
      for(Integer key: keyings){
         RecordsCollection score = this.lvlScores.get(key);
         sb.append(getResultForUser(score));
         sb.append(";");
      }
      return sb.toString();
  }
     
  String getResultForLvl(RecordsCollection userScore)
  {
    Iterator<UserScore> iterator = userScore.iterator();
    StringBuffer sb = new StringBuffer();
    while(iterator.hasNext()){
    UserScore user = iterator.next();
    if(sb.length() != 0){
    sb.append(";");
    }
    sb.append(user.getUserId());
    sb.append("=");
    sb.append(user.getScore());
    }
      return sb.toString();
  }
  
   String getResultForUser(RecordsCollection userScore)
  {
    Iterator<UserScore> iterate = userScore.iterator();
    StringBuffer sb = new StringBuffer();
    while(iterate.hasNext()){
    UserScore user = iterate.next();
    sb.append(user.getLvl());
    sb.append("=");
    sb.append(user.getScore());
    }
      return sb.toString();
  }
  
  private void createLockForLvl(int lvl){
  if(!lockForLvl(lvl)){ return;}
  this.lvlLock.lock();
  try{
      this.locks.put(lvl, new ReentrantReadWriteLock(true));
  }finally{
        this.lvlLock.unlock();
  }
  }
  
  public void submitNewScore(int id, int lvl, int score){
  createLockForLvl(lvl);
  RecordsCollection col = addScoreForLvl(lvl,id); 
  Lock read = getLock(lvl).readLock();
  try{
  read.lock();
  if(!col.canAddNewScore(score)){return;}
  }finally{
  read.unlock();
  }
  writeScore(id, lvl, score, col);
  }
  
  RecordsCollection addScoreForLvl(int lvl, int id){
  if(!this.lvlScores.containsKey(lvl)){
  this.lvlScores.put(lvl, new RecordsCollection(MAX_SIZE));
  this.userLvl.put(lvl,id);
  }
  return this.lvlScores.get(lvl);
  }
 
  private void writeScore(int id, int lvl,int score, RecordsCollection col){
  Lock write = getLock(lvl).writeLock();
  try{
  write.lock();
  col.addScore(score,id, lvl);
  }finally{
   write.unlock();
  }
 }
}
