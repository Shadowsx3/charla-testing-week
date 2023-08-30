use serde::{Deserialize, Serialize};
use std::str::FromStr;
use chrono::{DateTime, Utc};

#[derive(Debug, Deserialize, Serialize, PartialEq)]
pub enum Role {
    User,
    ApiAdmin,
}

impl FromStr for Role {
    type Err = ();

    fn from_str(input: &str) -> Result<Role, Self::Err> {
        match input {
            "User" => Ok(Role::User),
            "ApiAdmin" => Ok(Role::ApiAdmin),
            _ => Err(()),
        }
    }
}

fn default_role() -> Role {
    Role::User
}

#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct User {
    pub id: String,
    pub email: String,
    pub name: String,
    pub is_premium: bool,
    pub password: String,
    pub role: String,
    pub energy: i16,
    pub coins: i32,
    pub wins: i32,
    pub losses: i32,
    pub next_claim_energy_time: DateTime<Utc>,
    pub picture_id: i32
}

impl User {
    pub fn get_role(&self) -> Role {
        Role::from_str(self.role.as_str()).unwrap()
    }
}

#[derive(Debug, Deserialize, Serialize, Clone)]
pub struct PlayRequest {
    pub won: bool
}

#[derive(Debug, Serialize, Deserialize)]
pub struct TokenClaims {
    pub sub: String,
    pub iat: usize,
    pub exp: usize,
}


#[allow(non_snake_case)]
#[derive(Debug, Deserialize)]
pub struct RegisterUserRequest {
    pub email: String,
    pub name: String,
    pub password: String,
    #[serde(default = "default_role")]
    pub role: Role,
}

#[derive(Debug, Deserialize)]
pub struct LoginUserSchema {
    pub email: String,
    pub password: String,
}

#[allow(non_snake_case)]
#[derive(Debug, Serialize)]
pub struct FilteredUser {
    pub email: String,
    pub name: String,
    pub isPremium: bool,
    pub role: String,
    pub energy: i16,
    pub coins: i32,
    pub wins: i32,
    pub losses: i32,
    pub nextClaimEnergyTime: DateTime<Utc>,
}

#[derive(Serialize, Debug)]
pub struct UserData {
    pub user: FilteredUser,
}

#[derive(Serialize, Debug)]
pub struct UserResponse {
    pub status: String,
    pub data: UserData,
}

#[derive(Deserialize)]
pub struct UserInfo {
    pub user_id: String,
}

pub fn filter_user_record(user: &User) -> FilteredUser {
    FilteredUser {
        email: user.email.to_owned(),
        name: user.name.to_owned(),
        isPremium: user.is_premium,
        role: user.role.to_string(),
        energy: user.energy,
        coins: user.coins,
        wins: user.wins,
        losses: user.losses,
        nextClaimEnergyTime: user.next_claim_energy_time,
    }
}
