package edu.ssdut.compositeIDs;

import edu.ssdut.entities.Answer;
import edu.ssdut.entities.User;

import java.io.Serializable;

/**
 * Created by Gaomj on 2017/7/6.
 */
public class VoteId implements Serializable{
    private User user;
    private Answer answer;
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((user == null) ? 0 : user.getId().hashCode());
        result = PRIME * result + ((answer == null) ? 0 : answer.getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return (user.getId()==((VoteId)obj).user.getId())&&(answer.getId()==((VoteId)obj).answer.getId());
    }
}
