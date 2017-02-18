package data;

public class UserScore implements Comparable<UserScore>{
    Integer score;
    Integer userId;
    Integer lvl;

    public UserScore(Integer score,Integer id,int lvl){
    this.score = score;
    this.userId = id;
    this.lvl = lvl;
    }
    
    public int getLvl(){
    return this.lvl;
    }
    
    public Integer getScore(){
    return score;
    }
    
    public Integer getUserId(){
    return userId;
    }

    @Override
    public int compareTo(UserScore o) {
        return o.getScore().compareTo(this.score);
    }  
}
