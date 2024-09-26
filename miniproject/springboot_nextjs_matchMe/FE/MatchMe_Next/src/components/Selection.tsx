"use client";

import { useState } from "react";

interface SelectionProps {
  selectOne: string;
  selectTwo: string;
}

export function Selection({ selectOne, selectTwo }: SelectionProps) {
  const [selected, setSelected] = useState(""); // 라디오 버튼에서 선택된 값

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelected(event.target.value); // 선택된 값 업데이트
  };

  return (
    <div>
      <div>
        <label>
          <input
            type="radio"
            value={selectOne}
            checked={selected === selectOne}
            onChange={handleChange}
            name="options"
          />
          {selectOne}
        </label>
      </div>
      <div>
        <label>
          <input
            type="radio"
            value={selectTwo}
            checked={selected === selectTwo}
            onChange={handleChange}
            name="options"
          />
          {selectTwo}
        </label>
      </div>
    </div>
  );
}
