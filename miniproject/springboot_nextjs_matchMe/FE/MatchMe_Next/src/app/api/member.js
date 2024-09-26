// 사용자 생성 API 호출 함수
const url = `http://localhost:8080`;

export const createUser = async (userData) => {
  try {
    const response = await fetch(`${url}/api/auth/join`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userData),
    });
  
    if (!response.ok) {
      throw new Error("Failed to create user");
    }
  
    return response.data;
  } catch (e) {
    console.error(e);
  }
}

  
export const loginUser = async (userData) => {
  const response = await fetch(`${url}/api/auth/login`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(userData),
  });

  if (!response.ok) {
    throw new Error("Failed to login");
  }

  return response.json();
};

export const searchUser = async (keyword) => {
  const response = await fetch(`${url}/api/auth/search?keyword=${keyword}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    }
  })

  if (!response.ok) {
    throw new Error("Failed to Search");
  }
  
  return response.json();
}
