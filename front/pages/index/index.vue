<template>
	<view class="homeLayout pageBg">
		<custom-nav-bar title="推荐"></custom-nav-bar>
		<view class="banner">
			<swiper circular  indicator-dots indicator-color="rgba(255,255,255,0.5)"
				 indicator-active-color="#fff" autoplay>
				<swiper-item v-for="item in 3">
					<image src="../../common/images/banner1.jpg" mode="aspectFill"></image>
				</swiper-item>
			</swiper>
		</view>
		<view class="notice">
			<view class="notice-left">
				<uni-icons type="sound-filled" size="16"></uni-icons>
				<text class="notice-left-text">公告</text>
			</view>
			<view class="notice-center">
				<swiper vertical autoplay :interval="1500" :duration="300" circular>
					<navigator url="/pages/notice/detail">
						<swiper-item v-for="item in 4">文字内容文字内容文文字内容字内容文字内容</swiper-item>
					</navigator>
					
				</swiper>
			</view>
			<view class="notice-right">
				<uni-icons type="right" size="16" color="#333"></uni-icons>
			</view>
		</view>
		<view class="select">
			<common-title>
				<template #name>每日推荐</template>
				<template #custom>
					<view class="select-date">
						<uni-icons type="calendar" size="18"></uni-icons>
						<view class="select-date-text">
						<uni-dateformat :date="Date.now()" format="dd"></uni-dateformat>日
						</view>
					</view>
				</template>
			</common-title>
			<view class="select-content">
				<scroll-view scroll-x >
					<view class="select-content-box" v-for="item in 8" @click="goPreview">
							<image src="../../common/images/preview_small.webp" mode="aspectFill"></image>	
					</view>
				</scroll-view>
			</view>
		</view>
		<view class="theme">
			<common-title>
				<template #name>专题精选</template>
				<template #custom>
					<navigator url="" class="theme-more">More+</navigator>
				</template>
			</common-title>
			<view class="content">
				<theme-item v-for="item in 8" ></theme-item>
				<theme-item :isMore="true" ></theme-item>
			</view>
		</view>
	</view>
</template>

<script setup>
const goPreview = ()=>{
	uni.navigateTo({
		url:"/pages/preview/preview",
	})
}
</script>

<style lang="scss" scoped>
.homeLayout {
	.banner {
		width: 750rpx;
		padding: 30rpx 0;
		swiper {
			width: 750rpx;
			height: 340rpx;
			swiper-item {
				width: 100%;
				height: 100%;
				padding: 0 30rpx;
				image{
					width: 100%;
					height: 100%;
					border-radius: 10rpx;;
				}
			}
		}
	}
	.notice {
		width: 690rpx;
		height: 80rpx;
		line-height: 80rpx;
		background-color: #ccc;
		margin: 0 auto;
		border-radius: 40rpx;
		display:flex;
		.notice-left {
			width: 140rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			:deep(){
				.uni-icons {
					color:$brand-theme-color !important;
				}
			}
			
			.notice-left-text {
				color: $brand-theme-color;
				font-weight: 600;
				font-size: 28rpx;
			}
		}
		.notice-center {
			flex:1;
			swiper {
				height: 100%;
				swiper-item {
					height: 100%;
					font-size: 30rpx;
					color:#666;
					overflow: hidden;
					white-space: nowrap;
					text-overflow: ellipsis;
				}
			}
		}
		.notice-right {
			width: 70rpx;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}
	.select {
		padding-top: 50rpx;
		.select-content {
			width: 720rpx;
			margin-top: 30rpx;
			margin-left: 30rpx;
			scroll-view {
				white-space: nowrap;
				.select-content-box {
					width: 200rpx;
					height: 430rpx;
					display: inline-block;
					margin-right: 15rpx ;
					image {
						width: 100%;
						height: 100%;
						border-radius: 10rpx;
					}
				}
				.select-content-box:last-child {
					margin-right: 30rpx;
				}
			}
		}
		.select-date {
			:deep(){
				.uni-icons {
					color:$brand-theme-color !important;
				}
			}
			display: flex;
			align-items: center;
			.select-date-text {
				margin-left: 5rpx;
			}
		}
	}
	.theme {
		padding:50rpx 0;
		.theme-more {
			font-size: 32rpx;
			color:#888;
		}
		.content {
			margin-top: 30rpx;
			padding: 0 30rpx;
			display: grid;
			gap: 15rpx;
			grid-template-columns: repeat(3,1fr);
			
		}
	}
	
}
</style>


