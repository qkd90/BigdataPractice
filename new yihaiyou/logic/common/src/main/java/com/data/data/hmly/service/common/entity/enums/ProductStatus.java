package com.data.data.hmly.service.common.entity.enums;

public enum ProductStatus {
	UP("上架"), DOWN("下架"), DEL("删除"),
    UP_CHECKING("上架中"), DOWN_CHECKING("下架中"), REFUSE("拒绝"), CHECKING("审核中"), FAIL("被拒绝");

    private String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
