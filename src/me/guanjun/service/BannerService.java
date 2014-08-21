package me.guanjun.service;

import me.guanjun.persistent.model.Banner;

import java.util.List;

/**
 * Created by guanj on 2014/4/28.
 */
public interface BannerService {
    List getBanners(String title) throws RuntimeException, Exception;
    Banner getBanner(String bid) throws RuntimeException,Exception;
    boolean removeBanner(String bid) throws RuntimeException,Exception;
    boolean addBanner(Banner banner) throws RuntimeException,Exception;
    boolean modifyBanner(Banner banner) throws RuntimeException,Exception;

    List getBanners(String title, int offset, int limit) throws RuntimeException,Exception;
    int getCountBanners(String title) throws RuntimeException,Exception;
}
