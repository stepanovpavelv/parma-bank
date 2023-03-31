package parma.edu.auth.dto;

import lombok.Getter;

@Getter
public abstract class BaseResponseDto {
    protected boolean ok = true;
}