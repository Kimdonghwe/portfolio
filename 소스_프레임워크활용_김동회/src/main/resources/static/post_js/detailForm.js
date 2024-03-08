import {Pagination} from '/post_js/paging.js'

document.addEventListener('DOMContentLoaded', renderHTML);

renderHTML();
function renderHTML() {
  const $div = document.createElement('div');
  $div.innerHTML = `
<h3>게시글 조회</h3>
  <table>
    <tr>
      <td><label for="title">제목</label></td>
      <td><input class='bottom-border' type="text" id="title" name="title" required 
        ></td>
      <span class="errTitle err-msg" id="errTitle"></span>
      <span class="err-msg"  th:class="'ErrTitle err-msg on'" ></span>
    </tr>
    <tr>
      <td><label for="pname">작성자</label></td>
      <td><input class='bottom-border' type="text" id="pname" name="pname" 
        readonly></td>
    </tr>
    <tr>
      <td><label for="detail">내용</label></td>
      <td><textarea class='bottom-border' rows="30" cols="60" id="detail" name="detail"  required></textarea>
      </td>
      <span class="errDetail err-msg" id="errDetail"></span>
      <span class="err-msg" ></span>
    </tr>      
  </table>
  <div class="button_wrap">
    <input id="modifyBtn" type="submit" value="저장">
    <input id="cancel" type="reset" value="취소">
    <input id="listBtn" type="button" value="목록" >
  </div> `;

  document.body.appendChild($div);

}
