"use client";

import { useQuery } from "@tanstack/react-query";
import { questionListByMember } from "@/app/api/question";
import { Tabs } from "@/components/Tabs";

export interface Question {
  id: number;
  content: string;
  answers: [
    {
      id: number;
      content: string;
      correct: string;
    }
  ];
}

const QuestionList = () => {
  const { data, error, isLoading } = useQuery({
    queryKey: ["questionListByMember"],
    queryFn: questionListByMember,
  });

  if (isLoading) {
    return <p>isLoading...</p>;
  }

  if (error) {
    return <p>error...</p>;
  }

  return (
    <div className="w-full text-center">
      <h1 className="text-2xl font-bold mb-6">나를 맞춰봐 ! MATCH ME</h1>
      <Tabs questions={data} />
    </div>
  );
};

export default QuestionList;
