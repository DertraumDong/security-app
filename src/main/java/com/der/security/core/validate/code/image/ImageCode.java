package com.der.security.core.validate.code.image;

import com.der.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;

public class ImageCode extends ValidateCode {
    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public ImageCode(String code, int expireIn, BufferedImage image) {
        super(code,expireIn);
        this.image = image;
    }

}
