import React from "react";
import classNames from "classnames";

interface AnswerOptionProps {
  index: number;
  answerContent: string;
  correct: boolean;
  onAnswerChange: (
    index: number,
    field: string,
    value: string | boolean
  ) => void;
  onCorrectChange: (index: number) => void;
}

const AnswerOption: React.FC<AnswerOptionProps> = ({
  index,
  answerContent,
  correct,
  onAnswerChange,
  onCorrectChange,
}) => {
  return (
    <div className="flex flex-row items-center space-x-4 mt-4 w-full">
      {/* Input for answer text */}
      <input
        type="text"
        value={answerContent}
        onChange={(e) => onAnswerChange(index, "content", e.target.value)}
        placeholder={`보기 ${index + 1}`}
        className="border border-purple-300 p-4 rounded-lg w-3/4 text-black text-lg"
        required
      />

      {/* Button that acts like a radio button */}
      <button
        type="button"
        className={classNames(
          "p-4 w-1/4 rounded-lg border-2 transition-colors text-center",
          correct
            ? "bg-[#FFD37C] text-[#313492] border-[#FFD37C]"
            : "bg-gray-200 text-black border-gray-200 hover:bg-[#E6B86E] hover:border-[#E6B86E] transition"
        )}
        onClick={() => onCorrectChange(index)}
      >
        {correct ? "정답" : "오답"}
      </button>
    </div>
  );
};

export default AnswerOption;
