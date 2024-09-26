"use client";

import { ChangeEvent, FormEvent, useState } from "react";
import { MyButton } from "@/components/MyButton";
import { useMutation } from "@tanstack/react-query";
import { searchUser } from "../api/member";
import Input from "@/components/Input";
import { useRouter } from "next/navigation"; // Import the useRouter hook

interface searchUserProps {
  id: number;
  username: string;
  email: string;
}

export default function Search() {
  const [nickname, setNickname] = useState("");
  const [result, setResult] = useState<searchUserProps[]>([]);
  const router = useRouter(); // Initialize router

  const handleInputChange = (e: ChangeEvent<HTMLInputElement>) => {
    setNickname(e.target.value);
  };

  const mutation = useMutation({
    mutationFn: searchUser,
    onSuccess: (data) => {
      console.log("검색 성공");
      setResult(data);
      console.log(data[0].username);
    },
  });

  const handleSearch = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutation.mutate(nickname);
    console.log(`Searching for: ${nickname}`);
  };

  const handleUserClick = (userId: number) => {
    router.push(`/questions/member/${userId}`);
  };

  return (
    <div className="flex flex-col justify-center items-center">
      <h1 className="text-3xl font-bold mb-10">닉네임으로 친구 찾기</h1>
      <div className="flex flex-col justify-center items-center">
        <form
          onSubmit={handleSearch}
          className="flex items-center space-x-5 mb-5 w-[430px]"
        >
          <input
            className="border border-gray-300 rounded-lg px-5 py-4 text-black w-[300px] focus:outline-none focus:border-gray-300"
            type="text"
            placeholder="닉네임"
            value={nickname}
            onChange={handleInputChange}
          />
          <MyButton type="submit" content="찾기" />
        </form>
      </div>

      <div className="space-y-4 p-4">
        {result &&
          result.map((user) => (
            <div
              key={user.id}
              onClick={() => handleUserClick(user.id)} // Handle click event to route
              className="cursor-pointer w-[430px] bg-purple-100 shadow-md rounded-lg p-6 hover:bg-gray-100 transition duration-300"
            >
              <p className="text-2xl mb-3 font-semibold text-gray-800">
                {user.username}
              </p>
              <p className="text-xl text-gray-600 mb-10">{user.email}</p>
              <p className="text-xl text-center text-gray-600 border border-gray-600 rounded-md p-2">
                취향 맞추러 가기 👉
              </p>
            </div>
          ))}
      </div>
    </div>
  );
}
