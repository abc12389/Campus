package com.naughty.campus.dao;

import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Matches;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchesDao {

   // public int getMatchesByMassage(@Param("Matchesname") String Matchesname, @Param("password") String password);
    public List<Matches> getAllMatches(@Param("info") QueryInfo info);
    public int getMatchesCounts(@Param("info") QueryInfo info);
   // public int updateState(Integer id, Boolean state);
    //public int addMatches(Matches Matches);
   // public Matches getUpdateMatches(int id);
   // public int editMatches(Matches Matches);
    //public int deleteMatches(int id);
}
