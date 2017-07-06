package edu.ssdut.compositeID;

import edu.ssdut.entity.User;

import java.io.Serializable;

/**
 * Created by Gaomj on 2017/7/4.
 */
public class ConcernedId implements Serializable{
    private User user;
    private User concernedUser;
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((user == null) ? 0 : user.getId().hashCode());
        result = PRIME * result + ((concernedUser == null) ? 0 : concernedUser.getId().hashCode());
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
        return (user.getId()==((ConcernedId)obj).user.getId())&&(concernedUser.getId()==((ConcernedId)obj).concernedUser.getId());
    }
}
