"use client";

import { useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";
import Image from "next/image";
import { MyButton } from "@/components/MyButton";

export default function Result() {
  const searchParams = useSearchParams();
  const [score, setScore] = useState<number | null>(null);

  useEffect(() => {
    const scoreParam = searchParams.get("score");
    if (scoreParam) {
      setScore(parseInt(scoreParam));
    }
  }, [searchParams]);

  const titleList = [
    "우리 절교하자^^ㅎㅎ;;",
    "나에 대해 아는 게 없구나..",
    "이번 달 친구비 입금 드립니다.",
    "「〃♡우zl우정언저l나영원히♡〃",
  ];

  const imgList = [
    "/img/worst.jpeg", // Correct path to the public directory
    "/img/bad.jpeg",
    "/img/good.jpeg",
    "/img/best.jpeg",
  ];

  let title = "";
  let imgUrl = "";

  if (score === 0) {
    title = titleList[0];
    imgUrl = imgList[0];
  } else if (score === 1 || score === 2) {
    title = titleList[1];
    imgUrl = imgList[1];
  } else if (score === 3 || score === 4) {
    title = titleList[2];
    imgUrl = imgList[2];
  } else if (score === 5) {
    title = titleList[3];
    imgUrl = imgList[3];
  }

  return (
    <div className="flex flex-col justify-center items-center space-y-2">
      <h1 className="text-white font-bold text-center text-3xl mb-5">
        {title}
      </h1>
      <div className="pb-10">
        <Image src={imgUrl} alt="image" width={400} height={400} />
      </div>
      <div className="flex space-x-5">
        <MyButton route={`/search`} content="다시하기" />
        <MyButton route={`/create`} content="내 질문 생성하기" />
      </div>
    </div>
  );
}
