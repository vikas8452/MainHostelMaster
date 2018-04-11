package com.hostelmanager.hostelmaster;

/**
 * Created by hp on 1/9/2018.
 */

public class Work_with_us_users {

   private String cont_name, prop_name, tot_bed, tot_bedroom, add_1, add_2, state_string, country_string, city_string, post, cont_number, cont_number_2, manag_email, book_email;

   public Work_with_us_users() {

   }

   public Work_with_us_users(String prop_name, String tot_bedroom, String tot_bed, String add_1, String add_2, String state_string, String post, String country_string, String city_string, String cont_name, String manag_email, String book_email, String cont_number, String cont_number_2) {
      this.prop_name = prop_name;this.tot_bedroom = tot_bedroom;this.tot_bed = tot_bed;
      this.add_1 = add_1;
      this.add_2 = add_2;this.state_string = state_string; this.post = post;this.country_string = country_string;
      this.book_email = book_email;
      this.city_string = city_string;
      this.cont_name = cont_name; this.manag_email = manag_email;this.book_email=book_email;
      this.cont_number = cont_number;
      this.cont_number_2 = cont_number_2;

   }

   public String getCont_name() {
      return cont_name;
   }

   public String getProp_name() {
      return prop_name;
   }

   public String getTot_bed() {
      return tot_bed;
   }

   public String getTot_bedroom() {
      return tot_bedroom;
   }

   public String getAdd_1() {
      return add_1;
   }

   public String getAdd_2() {
      return add_2;
   }

   public String getState_string() {
      return state_string;
   }

   public String getCountry_string() {
      return country_string;
   }

   public String getCity_string() {
      return city_string;
   }

   public String getPost() {
      return post;
   }

   public String getCont_number() {
      return cont_number;
   }

   public String getCont_number_2() {
      return cont_number_2;
   }

   public String getManag_email() {
      return manag_email;
   }

   public String getBook_email() {
      return book_email;
   }
}

