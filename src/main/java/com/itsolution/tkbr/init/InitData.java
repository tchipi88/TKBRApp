/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itsolution.tkbr.init;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author tchipi
 */
public interface InitData {
    public void populateData(HttpServletRequest req) throws Exception;
}
