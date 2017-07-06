package mineward.core.cooldown;

public class CooldownToken {

    public long Time;
    public long StartTime;
    public String Data;
    public String Coolfor;

    public CooldownToken(long Time, long StartTime, String Data, String Coolfor) {
        this.Time = Time;
        this.StartTime = StartTime;
        this.Data = Data;
        this.Coolfor = Coolfor;
    }

}
