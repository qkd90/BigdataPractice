INSERT INTO store_bank_card(
	bank_card_id,
  state,
  name,
  ID_card,
  bank_name,
  bank_card,
  mobile,
  create_time,
  delet,
  connect_Id,
  connect_type
  <#if bankCard.bankAddress?? && bankCard.bankAddress?length gt 0>
    ,bank_address
  </#if>
) VALUES(
  "${bankCard.bankCardId?c}",
  "${bankCard.state}",
  "${bankCard.name}",
  "${bankCard.idCard}",
  "${bankCard.bankName}",
  "${bankCard.bankCard}",
  "${bankCard.mobile}",
  "${bankCard.createTime?string('yyyy-MM-dd HH:mm:ss')}",
  "${bankCard.delet}",
  "${bankCard.connectId?c}",
  "${bankCard.connectType}"
  <#if bankCard.bankAddress?? && bankCard.bankAddress?length gt 0>
    ,"${bankCard.bankAddress}"
  </#if>
)