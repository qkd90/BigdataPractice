package com.zuipin.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;

import com.zuipin.entity.Member;
import com.zuipin.entity.TMemberGrade;
import com.zuipin.entity.TMemberSpecialCashback;
import com.zuipin.entity.TMerchantStore;
import com.zuipin.entity.VProduct;
import com.zuipin.util.ArithUtil;
import com.zuipin.util.StringUtils;

@SuppressWarnings("serial")
public class Cart implements java.io.Serializable {
	/**
	 * 订单专用返现总金额
	 */
	private double						totalSpecialReturnAmt	= 0.0;
	/**
	 * 订单专用返现总金额
	 */
	private double						totalUniversalReturnAmt	= 0.0;
	/**
	 * 订单应付总金额
	 */
	private double						totalAmt				= 0.0;
	/**
	 * 订单未抵扣原始总金额
	 */
	private Double						totalSaleAmt			= 0.0;
	/**
	 * 订单优惠券抵扣金额
	 */
	private Double						totalCouponAmt			= 0.0;

	/**
	 * 商品数量
	 */
	private int							totalProdCount			= 0;
	/**
	 * 会员对象
	 */
	private Member						member;
	/**
	 * 会员等级
	 */
	private TMemberGrade				memberGrade;
	/**
	 * .运费
	 */
	private double						totalCarryCost;
	/**
	 * .通用返现抵扣
	 */
	private boolean						isDeductible			= false;

	/**
	 * .通用返现抵扣金额
	 */
	private double						totalDeductibleAmt;
	/**
	 * .店铺列表
	 */
	private Map<Long, MerchantStore>	merchantStores			= new HashMap<Long, MerchantStore>();

	public Cart() {

	}

	public Cart(Member member, boolean isDeductible) {
		this.member = member;
		this.isDeductible = isDeductible;
	}

	public void addCart(TMerchantStore merchantStore, VProduct product, double count, boolean isDeductible, TMemberSpecialCashback specialCashback,
			List<CouponItem> couponList) {
		CartItem cartItem = new CartItem(product, count);
		// 单价
		cartItem.setSalePrice(product.getSalePrice());
		cartItem.setTotalSaleAmt(ArithUtil.mul(cartItem.getSalePrice(), count));
		MerchantStore store = merchantStores.get(merchantStore.getId());
		if (store == null) {
			// 创建一个店铺model复制店铺属性
			store = copyStore(store, merchantStore, specialCashback);
		}
		cartItem.setZyGiftCashbackAmt(ArithUtil.mul(store.getZyPresentCashback(), cartItem.getTotalSaleAmt()));
		cartItem.setTyGiftCashbackAmt(ArithUtil.mul(store.getTyPresentCashback(), cartItem.getTotalSaleAmt()));
		store.setDeductible(isDeductible);
		if (this.member != null) {
			if (store.isDeductible()) {
				if (store.getMemberSpecialCashback() != null) {
					double lastTotalAmt = ArithUtil.mul(cartItem.getTotalSaleAmt(), store.getZyDeductionCashback());
					double lastCard = ArithUtil.sub(specialCashback.getCardBalance(), store.getTotalZyDeductibleCashbackAmt());
					if (lastTotalAmt >= lastCard) {
						cartItem.setZyDeductibleCashbackAmt(lastCard);
					} else {
						cartItem.setZyDeductibleCashbackAmt(lastTotalAmt);
					}
					// 计算专返赠送金额 = 专返赠送比例 * (商品总金额-抵扣金额 )
					cartItem.setZyGiftCashbackAmt(ArithUtil.mul(store.getZyPresentCashback(),
							ArithUtil.sub(cartItem.getTotalSaleAmt(), cartItem.getZyDeductibleCashbackAmt())));
				}
			}
			if (this.isDeductible) {
				double lastTotalAmt = ArithUtil.mul(cartItem.getTotalSaleAmt(), store.getTyDeductionCashback());
				double lastCard = ArithUtil.sub(member.getCardBalance(), totalDeductibleAmt);
				if (lastTotalAmt >= lastCard) {
					cartItem.setTyDeductibleCashbackAmt(lastCard);
				} else {
					cartItem.setTyDeductibleCashbackAmt(lastTotalAmt);
				}
				// 计算通返赠送金额 = 通返赠送比例 * (商品总金额-抵扣金额 )
				cartItem.setTyGiftCashbackAmt(ArithUtil.mul(store.getTyPresentCashback(),
						ArithUtil.sub(cartItem.getTotalSaleAmt(), cartItem.getTyDeductibleCashbackAmt())));
			}
		}
		cartItem.setTotalAmt(ArithUtil.sub(cartItem.getTotalSaleAmt(), cartItem.getZyDeductibleCashbackAmt()));
		// 店铺专返赠送金额小计
		store.setTotalZyGiftCashbackAmt(ArithUtil.add(store.getTotalZyGiftCashbackAmt(), cartItem.getZyGiftCashbackAmt()));
		// 店铺通返赠送金额小计
		store.setTotalTyGiftCashbackAmt(ArithUtil.add(store.getTotalTyGiftCashbackAmt(), cartItem.getTyGiftCashbackAmt()));
		// 店铺专返抵扣金额小计
		store.setTotalZyDeductibleCashbackAmt(ArithUtil.add(store.getTotalZyDeductibleCashbackAmt(), cartItem.getZyDeductibleCashbackAmt()));
		// 店铺通返抵扣金额小计
		store.setTotalTyDeductibleCashbackAmt(ArithUtil.add(store.getTotalTyDeductibleCashbackAmt(), cartItem.getTyDeductibleCashbackAmt()));
		// 店铺商品金额小计
		store.setTotalAmt(ArithUtil.add(store.getTotalAmt(), cartItem.getTotalAmt()));
		store.getCartItems().add(cartItem);
		// 店铺优惠券列表
		store.setCouponList(couponList);
		// 订单专用返现总金额
		this.totalSpecialReturnAmt = ArithUtil.add(this.totalSpecialReturnAmt, cartItem.getZyGiftCashbackAmt());
		// 订单通用返现金额
		this.totalUniversalReturnAmt = ArithUtil.add(this.totalUniversalReturnAmt, cartItem.getTyGiftCashbackAmt());
		// 订单通用返现抵扣金额
		this.totalDeductibleAmt = ArithUtil.add(this.totalDeductibleAmt, cartItem.getTyDeductibleCashbackAmt());
		// 订单未抵扣金额
		this.totalSaleAmt = ArithUtil.add(this.totalSaleAmt, cartItem.getTotalAmt());
		// 订单应付总金额
		this.totalAmt = ArithUtil.add(this.totalAmt, ArithUtil.sub(cartItem.getTotalAmt(), cartItem.getTyDeductibleCashbackAmt()));
		this.totalProdCount++;
		merchantStores.put(store.getStoreId(), store);
	}

	public void countCarryCost(Cookie cookie) {
		MerchantStore store = null;
		for (Long id : this.merchantStores.keySet()) {
			store = this.merchantStores.get(id);
			if (store.getFreeCarryCost() > store.getTotalAmt()) {
				this.totalCarryCost = ArithUtil.add(this.totalCarryCost, store.getCarryCost());
			} else {
				store.setFreeCarry(true);
			}
		}
		if (cookie == null)
			return;
		String value = StringUtils.decodeUTF8(cookie.getValue());
		if (StringUtils.isBlank(value))
			return;
		StringTokenizer stk = new StringTokenizer(value, ",");
		String val = "";
		String[] array = null;
		while (stk.hasMoreTokens()) {
			val = stk.nextToken();
			if (StringUtils.isBlank(val))
				continue;
			array = val.split("\\|");
			store = merchantStores.get(Long.valueOf(array[0]));
			if (store == null)
				continue;
			store.setDeliveryMode(Integer.valueOf(array[1]));
			// 送货上门
			if (store.getDeliveryMode() == 2) {
				// 不免邮
				if (!store.isFreeCarry()) {
					this.totalCarryCost = ArithUtil.add(this.totalCarryCost, store.getCarryCost());
					store.setTotalAmt(ArithUtil.add(store.getTotalAmt(), store.getCarryCost()));
					this.totalSaleAmt = ArithUtil.add(this.totalSaleAmt, store.getCarryCost());
					this.totalAmt = ArithUtil.add(this.totalAmt, store.getCarryCost());
				}
			}
		}
	}

	private MerchantStore copyStore(MerchantStore store, TMerchantStore merchantStore, TMemberSpecialCashback memberSpecialCashback) {
		store = new MerchantStore();
		store.setStoreId(merchantStore.getId());
		store.setCarryCost(merchantStore.getCarryCost());
		store.setFreeCarryCost(merchantStore.getFreeCarryCost());
		store.setServiceQq(merchantStore.getServiceQq());
		store.setStoreName(merchantStore.getStoreName());
		store.setStoreType(merchantStore.getStoreType());
		store.setStoreAddress(merchantStore.getAddress());
		store.setZyPresentCashback(merchantStore.getZyPresentCashback());
		store.setZyDeductionCashback(merchantStore.getZyDeductionCashback());
		store.setTyPresentCashback(merchantStore.getTyPresentCashback());
		store.setTyDeductionCashback(merchantStore.getTyDeductionCashback());
		store.setMemberSpecialCashback(memberSpecialCashback);
		store.setStorePhone(merchantStore.getServicePhone());
		return store;
	}

	public double getTotalSpecialReturnAmt() {
		return totalSpecialReturnAmt;
	}

	public void setTotalSpecialReturnAmt(double totalSpecialReturnAmt) {
		this.totalSpecialReturnAmt = totalSpecialReturnAmt;
	}

	public double getTotalUniversalReturnAmt() {
		return totalUniversalReturnAmt;
	}

	public void setTotalUniversalReturnAmt(double totalUniversalReturnAmt) {
		this.totalUniversalReturnAmt = totalUniversalReturnAmt;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public int getTotalProdCount() {
		return totalProdCount;
	}

	public void setTotalProdCount(int totalProdCount) {
		this.totalProdCount = totalProdCount;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public TMemberGrade getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(TMemberGrade memberGrade) {
		this.memberGrade = memberGrade;
	}

	public Map<Long, MerchantStore> getMerchantStores() {
		return merchantStores;
	}

	public void setMerchantStores(Map<Long, MerchantStore> merchantStores) {
		this.merchantStores = merchantStores;
	}

	public double getTotalCarryCost() {
		return totalCarryCost;
	}

	public void setTotalCarryCost(double totalCarryCost) {
		this.totalCarryCost = totalCarryCost;
	}

	public boolean isDeductible() {
		return isDeductible;
	}

	public void setDeductible(boolean isDeductible) {
		this.isDeductible = isDeductible;
	}

	public double getTotalDeductibleAmt() {
		return totalDeductibleAmt;
	}

	public void setTotalDeductibleAmt(double totalDeductibleAmt) {
		this.totalDeductibleAmt = totalDeductibleAmt;
	}

	public Double getTotalSaleAmt() {
		return totalSaleAmt;
	}

	public void setTotalSaleAmt(Double totalSaleAmt) {
		this.totalSaleAmt = totalSaleAmt;
	}

	public Double getTotalCouponAmt() {
		return totalCouponAmt;
	}

	public void setTotalCouponAmt(Double totalCouponAmt) {
		this.totalCouponAmt = totalCouponAmt;
	}
}
