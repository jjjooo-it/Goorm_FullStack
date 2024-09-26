const url = `http://localhost:8080`;

export const createQuestion = async (questionData) => {
  const accessToken = localStorage.getItem("accessToken");
  console.log(JSON.stringify(questionData));
  console.log(questionData);
  const response = await fetch(`${url}/api/questions/create`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${accessToken}`,
    },

    body: JSON.stringify(questionData),
  });

  if (!response.ok) {
    throw new Error("생성 실패");
  }

  return response;
};

export const questionListByMember = async () => {
  const accessToken = localStorage.getItem("accessToken");
  const response = await fetch(`${url}/api/questions/member/1`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${accessToken}`,
    },
    
  });

  if (!response.ok) {
    throw new Error("생성 실패");
  }

  return response.json();
}