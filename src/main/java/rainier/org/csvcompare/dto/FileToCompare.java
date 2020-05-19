package rainier.org.csvcompare.dto;

public class FileToCompare<T> {
  private T curFile;

  private T targetFile;

  public void setCurFile(T curFile) {
    this.curFile = curFile;
  }

  public void setTargetFile(T targetFile) {
    this.targetFile = targetFile;
  }

  public T getCurFile() {
    return curFile;
  }

  public T getTargetFile() {
    return this.targetFile;
  }
}
