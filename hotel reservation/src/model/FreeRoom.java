package model;

public class FreeRoom extends Room{

    public FreeRoom(final String roomNum, final RoomType number)
    {
        super(roomNum,0.00, number);
    }


    @Override
    public final String toString(){
        return super.toString();
    }
    @Override
    public boolean equals(Object o){
        return super.equals(o);
    }
    @Override
    public int hashCode(){
        return super.hashCode();
    }


}