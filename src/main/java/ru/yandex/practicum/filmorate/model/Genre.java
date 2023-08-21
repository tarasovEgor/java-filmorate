package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Genre implements Comparable<Genre>{
    private Integer id;
    private String name;

    @Override
    public int compareTo(Genre o) {
        if (o == null) {return 1;} //satisfies your null student requirement
        return this.id.compareTo(o.id);
    }
}
