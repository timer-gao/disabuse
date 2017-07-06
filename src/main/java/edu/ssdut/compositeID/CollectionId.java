package edu.ssdut.compositeID;

import edu.ssdut.entity.Question;
import edu.ssdut.entity.User;

import java.io.Serializable;

/**
 * Created by Gaomj on 2017/7/6.
 */
public class CollectionId implements Serializable{
    private User user;
    private Question question;

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((user == null) ? 0 : user.getId().hashCode());
        result = PRIME * result + ((question == null) ? 0 : question.getId().hashCode());
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
        return (user.getId()==((CollectionId)obj).user.getId())&&(question.getId()==((CollectionId)obj).question.getId());
    }
}
